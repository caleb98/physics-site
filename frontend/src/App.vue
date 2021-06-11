<template>
	<div class="container">
		<div class="row">
			<Navbar/>
		</div>
		<div class="row justify-content-center">
			<HelloWorld class="col-10"/>
		</div>
	</div>
</template>


<script>
import HelloWorld from './components/HelloWorld.vue'
import Navbar from './components/Navbar.vue'

export default {
  name: 'App',
  components: {
	HelloWorld,
	Navbar
  }
}
</script>


<style>

</style>