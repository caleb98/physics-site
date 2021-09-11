<template>
	<CSRFTokenManager/>
	<div class="container-fluid">
		<div class="row">
			<Navbar/>
		</div>
	</div>
</template>


<script>
import CSRFTokenManager from '../../components/CSRFTokenManager.vue'
import Navbar from '../../components/Navbar.vue'

export default {
	name: 'App',
	components: {
		CSRFTokenManager,
		Navbar
	}
}
</script>


<style>

</style>