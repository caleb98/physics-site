<template>
	<div>{{msg}}</div>
</template>


<script>
export default {
	name: 'HelloWorld',

	data() {
		return {
			msg: ''
		}
	},

	mounted() {
		fetch("/api/messages/hello")
			.then((response) => response.text())
			.then((data) => this.msg = data)
	}
}
</script>


<style scoped>

</style>