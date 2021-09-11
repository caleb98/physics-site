<template>
	<div class="container-lg p-3">
		<div class="row">
			<h4>Existing Courses</h4>
		</div>
		<div class="row">
			<div class="col-sm-6 col-md-4 col-lg-3" v-for="course in courses" :key="course.name" @click="editingCourse = course.name">
				<div class="course-card" :class="{ selected: course.name === editingCourse }">
					<h6>{{course.name}}</h6>
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

		<div class="row mt-5">
			<h4 v-if="!editingCourse">Edit Course</h4>
			<h4 v-if="editingCourse">Editing "{{editingCourse}}"</h4>
		</div>
		<div class="row">
			<p v-if="!editingCourse">Click a course above to edit.</p>
		</div>
	</div>
</template>

<script>
import AdminNewCourse from './AdminNewCourse.vue';
import $ from 'jquery';

export default {
	name: "AdminCourseManager",
	components: {
		AdminNewCourse
	},

	beforeMount() {
		this.loadCourses();
	},

	data() {
		return {
			addingCourse: false,
			editingCourse: null,
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
		}
	}
}
</script>

<style>
.course-card {
	padding: 5px;
	border: 1px solid rgba(0, 0, 0, 0.5);
	box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2);
}

.course-card > p {
	margin: 0px;
}

.selected {
	background-color: rgba(0, 0, 0, 0.05);
	border: 1px solid rgba(240, 150, 0, 1);
	box-shadow: 2px 2px 2px 1px rgba(240, 150, 0, 1);
}
</style>