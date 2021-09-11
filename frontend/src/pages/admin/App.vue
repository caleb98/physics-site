<!-- Registration Page -->

<template>
	<CSRFTokenManager/>
	<div>
		<div class="container-fluid">
			<div class="row">
				<Navbar/>
			</div>
		</div>
		<div class="container-lg">
			<AdminCourseManager/>
		</div>
	</div>
</template>


<script>
import CSRFTokenManager from '../../components/CSRFTokenManager.vue'
import Navbar from '../../components/Navbar.vue'
import AdminCourseManager from '../../components/AdminCourseManager.vue'

export default {
	name: 'App',
	components: {
		CSRFTokenManager,
		Navbar,
		AdminCourseManager
	}
}
</script>


<style scoped>

</style>