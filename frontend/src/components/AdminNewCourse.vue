<template>
	<div class="input-group mb-3">
		<span class="input-group-text">Course Name</span>
		<input type="text" class="form-control" v-model="courseName">
	</div>
	<div class="input-group mb-3">
		<span class="input-group-text">Description</span>
		<textarea class="form-control" rows="4" v-model="courseDesc"></textarea>
	</div>
	<div>
		<button type="button" class="btn btn-primary me-3" @click="submit">Create</button>
		<button type="button" class="btn btn-secondary" @click="$emit('creationCancelled')">Cancel</button>
	</div>
	<p class="text-danger" v-if="message">{{message}}</p>
</template>

<script>
import $ from 'jquery'

export default {
	name: "AdminNewCourse",

	data() {
		return {
			courseName: "",
			courseDesc: "",
			message: null
		}
	},

	methods: {
		submit() {
			if(this.validateInput()) {
				this.message = null;
				$.post(
					"/course/create",
					{
						name: this.courseName,
						description: this.courseDesc
					},
					(data) => {
						if(data.isError) {
							this.message = "Error: " + data.message;
						}
						else {
							this.$emit("courseCreated");
						}
					}
				)
			}
		},

		validateInput() {
			// Check that there's a course name
			if(this.courseName.replace(" ", "").length == 0) {
				this.message = "Please enter a course name.";
				return false;
			}

			// Check that there's a course description
			if(this.courseDesc.replace(" ", "").length == 0) {
				this.message = "Please enter a course description.";
				return false;
			}

			return true;
		}
	},

	emits: [
		'courseCreated',
		'creationCancelled'
	]
}
</script>

<style>

</style>