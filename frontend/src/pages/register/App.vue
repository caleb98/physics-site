<!-- Registration Page -->

<template>
	<div>
		<div class="container-fluid">
			<div class="row">
				<Navbar/>
			</div>
		</div>
		<div class="container-lg">
			<div class="row justify-content-center">
				<div id="form-container" class="mt-3 p-3 col-10 col-sm-8 col-md-6 col-lg-4">
					<RegistrationForm/>
				</div>
			</div>
		</div>
	</div>
</template>


<script>
import Navbar from '../../components/Navbar.vue'
import RegistrationForm from '../../components/RegistrationForm.vue'

export default {
	name: 'App',
	components: {
		Navbar,
		RegistrationForm
	}
}
</script>


<style scoped>
#form-container {
	border: 1px solid gray;
	border-radius: 5px;
}
</style>