<template>
	<ul class="nav nav-tabs mt-3">
		<li class="nav-item" role="presentation">
			<button class="nav-link active" id="courses-tab" data-bs-toggle="tab" data-bs-target="#courses" type="button" role="tab" aria-controls="courses" aria-selected="true" @click="saveEditor()">Manage Courses</button>
		</li>
		<li>
			<button class="nav-link" id="editor-tab" data-bs-toggle="tab" data-bs-target="#editor" type="button" role="tab" aria-controls="editor" aria-selected="false">Edit Course</button>
		</li>
	</ul>

	<div class="tab-content" id="admin-tab-content">

		<!-- TAB PANE CONTENT FOR COURSE MANAGEMENT -->
		<div class="tab-pane fade show active" id="courses" role="tabpanel" aria-labelledby="courses-tab">
			<div class="container-lg p-3 gy-5">
				<div class="d-flex justify-content-center" v-if="successMessage || errorMessage">
					<p class="text-success" v-if="successMessage">{{successMessage}}</p>
					<p class="text-danger" v-if="errorMessage">{{errorMessage}}</p>
				</div>
				<div class="row">
					<h4>Existing Courses</h4>
				</div>
				<div class="row gy-3">
					<div class="col-sm-6 col-md-4 col-lg-3" v-for="course in courses" :key="course.name" @click="clickCourse(course)">
						<div class="course-card" :class="{ selected: course == editingCourse }">
							<h5>{{course.name}}</h5>
							<p>{{course.description}}</p>
						</div>
					</div>
				</div>

				<div class="row mt-5">
					<h4>Manage Courses</h4>
				</div>
				<div class="row" v-if="!addingCourse">
					<div class="col-auto pe-3">
						<button type="button" class="col-sm btn btn-primary" @click="addingCourse = !addingCourse">Add Course</button>
					</div>
				</div>
				<div class="row" v-if="addingCourse">
					<div class="col-sm-10 col-md-7 col-lg-6">
						<AdminNewCourse @course-created="updateCourses()" @creation-cancelled="addingCourse = false"></AdminNewCourse>
					</div>
				</div>
			</div>
		</div>
		<!-- END COURSE MANAGEMENT TAB -->

		<!-- TAB PANE CONTENT FOR COURSE EDITOR -->
		<div class="tab-pane fade" id="editor" role="tabpanel" aria-labelledby="editor-tab">
			<div class="container-lg p-3 gy-5" v-if="!editingCourse">
				<div class="row">
					<h4>Edit Course</h4>
				</div>
				<div class="row">
					<p>Select a course from the "Manage Courses" tab to edit it here.</p>
				</div>
			</div>
			<div class="container-lg p-3 gy-5" v-if="editingCourse">
				<AdminCourseEditor :editing="editingCourse" :editor-has-changes="editorHasChanges" @course-changed="editorHasChanges = true" @course-saved="editorSaved()" @save-error="saveError" ref="editor"/>
			</div>
		</div>
		<!-- END COURSE MANAGEMENT TAB -->

	</div>
</template>

<script>
import AdminNewCourse from './AdminNewCourse.vue';
import AdminCourseEditor from './AdminCourseEditor.vue'
import $ from 'jquery';

export default {
	name: "AdminCourseManager",
	components: {
		AdminNewCourse,
		AdminCourseEditor
	},

	beforeMount() {
		this.loadCourses();
	},

	data() {
		return {
			addingCourse: false,
			editingCourse: null,
			editorHasChanges: false,
			successMessage: null,
			errorMessage: null,
			courses: []
		}
	},

	methods: {
		updateCourses() {
			this.addingCourse = false;
			this.loadCourses();
		},

		loadCourses() {
			$.get(
				"/course/data",
				(data) => {
					this.courses = data;
				}
			);
		},

		clickCourse(course) {
			if(this.editorHasChanges) {
				this.errorMessage = "The course you are currently editing has unsaved changes! Save the changes before switching to a new course.";
				return;
			}

			if(this.editingCourse == course) {
				this.editingCourse = null;
			}
			else {
				this.editingCourse = course;
			}
		},

		saveEditor() {
			if(this.editorHasChanges) {
				this.$refs.editor.save();
			}
		},

		editorSaved() {
			this.editorHasChanges = false;
			this.errorMessage = null;
			this.loadCourses();
			this.successMessage = "Saved course " + this.editingCourse.name;
			setTimeout(() => {
				this.successMessage = null;
			}, 5000)
		},

		saveError(error) {
			this.errorMessage = "An error occurred while trying to save the course you are currently editing: " + error + " Consider returning to the editor and saving manually, or making a local backup copy.";
		}
	}
}
</script>

<style scoped>
.course-card {
	padding: 5px;
	border: 2px solid rgba(0, 0, 0, 0.5);
	box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2);
	border-radius: 5px;
	height: 100%;
	background-color: white;
}

.course-card > * {
	margin: 0px;
}

.selected {
	padding: 4px;
	border: 3px solid var(--secondary-color);
}

.nav-tabs {
	border-color: var(--secondary-color);
	border-width: 0px 0px 3px 0px;
}

.nav-tabs .nav-link.active {
	background-color: var(--secondary-color);
	border-color: var(--secondary-color);
	color: white;
}

.nav-tabs .nav-link:hover {
	border-color: var(--secondary-color);
}

.tab-content {
	background-color: rgb(245, 245, 245);
	border-style: solid;
	border-width: 0px 1px 1px 1px;
	border-color: rgb(222, 222, 222);
}
</style>