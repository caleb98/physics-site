<template>
	<div class="row">

		<div class="col-3">

			<h6>Settings</h6>
			<ul class="list-group mb-4">
				<li class="list-group-item" :class="{active: selectedSettings == 'General'}" @click="selectSetting('General')">General</li>
			</ul>
			
			<h6>Units</h6>
			<ol class="list-group list-group-numbered mb-2" v-if="editingCourse.units.length > 0">
				<li class="list-group-item" v-for="unit in editingCourse.units" :key="unit.name" @click="selectUnit(unit)" :class="{active: selectedUnit === unit}">
					{{unit.title}}
				</li>
			</ol>
			<p v-if="editingCourse.units.length == 0">No units yet! Get started by adding one.</p>
			<div class="container-fluid p-0 mb-3">
				<div class="row">
					<div class="col btn-group">
						<button type="button" class="btn btn-primary w-100" @click="newUnit()">Add Unit</button>
						<button type="button" class="btn btn-primary w-100" @click="newLesson()" v-if="selectedUnit">Add Lesson</button>
					</div>
				</div>
			</div>

			<div class="container-fluid p-0">
				<div class="row">
					<div class="col">
						<button type="button" class="btn" :class="{'btn-success': !editorHasChanges, 'btn-warning': editorHasChanges}" @click="save()">
							{{editorHasChanges ? 'Save Changes *' : 'Save Changes'}}
						</button>
					</div>
				</div>

				<div class="row">
					<div class="col">
						<button type="button" class="btn btn-secondary mt-2" @click="downloadBackup()">Download Backup</button>
					</div>
				</div>
				
				<transition name="fade">
					<div class="row" v-if="saveError">
						<div class="col">
							<p class="text-danger m-0">{{saveError}}</p>
						</div>
					</div>
				</transition>
			</div>
		</div>

		<!-- GENERAL SETTINGS -->
		<div class="col" v-if="selectedSettings == 'General'">
			<div class="d-flex justify-content-center">
				<h4>General Settings</h4>
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text" id="course-name-editor-label">Course Name</span>
				<input type="text" class="form-control" v-model="editingCourse.name" @change="$emit('courseChanged')" aria-label="Course Name" aria-describedby="course-name-editor-label">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text" id="course-desc-editor-label">Description</span>
				<textarea class="form-control" rows="4" aria-label="Course Description" v-model="editingCourse.description" @change="$emit('courseChanged')"></textarea>
			</div>
		</div>

		<!-- UNIT SETTINGS -->
		<div class="col" v-else-if="selectedUnit">
			<div class='d-flex justify-content-center'>
				<h3>{{editingCourse.name}}</h3>
			</div>
			<div class="d-flex justify-content-center">
				<h4>Unit Settings</h4>
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text" id="course-name-editor-label">Course Name</span>
				<input type="text" class="form-control" v-model="selectedUnit.title" @change="$emit('courseChanged')" aria-label="Course Name" aria-describedby="course-name-editor-label">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text" id="course-desc-editor-label">Description</span>
				<textarea class="form-control" rows="4" aria-label="Course Description" v-model="selectedUnit.description" @change="$emit('courseChanged')"></textarea>
			</div>
			<!-- <div class="d-flex justify-content-center">
				<button type="button" class="btn btn-primary" @click="newUnit()">New Lesson</button>
			</div> -->
		</div>

	</div>
</template>

<script scoped>
import $ from 'jquery';

export default {
	name: "AdminCourseEditor",

	props: {
		editing: Object,
		editorHasChanges: Boolean,
	},

	data() {
		return {
			originalName: this.editing.name,
			editingCourse: this.createEditCopy(this.editing),

			selectedSettings: 'General',
			selectedUnit: null,
			selectedLesson: null,

			saveError: null
		}
	},

	emits: {
		courseChanged: null,
		courseSaved: null,
		saveError: (error) => {
			if(error) {
				return true;
			}
			else {
				console.warn("Invalid save-error event payload!");
				return false;
			}
		}
	},

	watch: {
		editing(newCourse) {
			this.editingCourse = this.createEditCopy(newCourse)
			this.originalName = newCourse.name;
		}
	},

	methods: {
		hasSelection() {
			return this.selectedUnit != null || this.selectedLesson != null;
		},

		deselect() {
			this.selectedSettings = null;
			this.selectedUnit = null;
			this.selectedLesson = null;
		},

		selectSetting(setting) {
			this.deselect();
			this.selectedSettings = setting;
		},

		selectUnit(unit) {
			this.deselect();
			this.selectedUnit = unit;
		},

		selectLesson(lesson) {
			this.deselect();
			this.selectedLesson = lesson;
		},

		newUnit() {
			this.editingCourse.units.push({
				title: "New Unit",
				description: "No description added yet!",
				lessons: []
			});
			this.$emit('courseChanged');
		},

		save() {
			this.saveError = null;
			
			// Create deferred 
			let rename = $.Deferred();
			if(this.editingCourse.name != this.originalName) {

				// PUT Request if rename is needed, accept/reject
				// the deferred based on result.
				$.ajax({
					url: "/course/rename",
					type: "PUT",
					data: {oldName: this.originalName, newName: this.editingCourse.name},
					success: (response) => {
						if(response.isError) {
							this.saveError = response.message;
							this.$emit('saveError', this.saveError);
							rename.reject();
						}
						else {
							this.originalName = this.editingCourse.name;
							rename.resolve();
						}
					},
					error: (_xhr, _status, error) => {
						this.saveError = error;
						this.$emit('saveError', this.saveError);
						rename.reject();
					}
				});
			}

			// No rename needed, resolve the deferred immediately.
			else {
				rename.resolve();
			}

			// After renaming is finished, update other data
			$.when(rename)
			.done(() => {
				$.ajax({
					url: "/course/update",
					type: "PUT",
					contentType: "application/json",
					data: JSON.stringify(this.editingCourse),
					success: (response) => {
						if(response.isError) {
							this.saveError = response.message;
							this.$emit('saveError', this.saveError);
						}
						else {
							this.$emit('courseSaved');
						}
					},
					error: (_xhr, _status, error) => {
						this.saveError = error;
						this.$emit('saveError', this.saveError);
					}
				});
			});

		},

		downloadBackup() {
			var data = JSON.stringify(this.editingCourse);
			var blob = new Blob([data], {type: "text/plain"});
			var a = document.createElement('a');
			a.download = this.editingCourse.name.toLowerCase().replace(" ", "_") + ".json";
			a.href = window.URL.createObjectURL(blob);
			a.click();
		},

		createEditCopy(course) {
			var copy = {
				name: course.name,
				description: course.description,
				units: []
			};

			for(var unitId in course.units) {
				var unit = course.units[unitId];
				var unitCopy = {
					title: unit.title,
					description: unit.description,
					lessons: []
				};

				for(var lessonId in unit.lessons) {
					var lesson = unit.lessons[lessonId];
					var lessonCopy = {
						title: lesson.title
					}

					unitCopy.lessons.push(lessonCopy);
				}

				copy.units.push(unitCopy);
			}
			
			return copy;
		}
	}
}
</script>

<style>

</style>