<template>
	<nav id="navbar" class="navbar navbar-expand-md navbar-light">
		<div class="container-lg">

			<!-- Website Title -->
			<a class="navbar-brand" href="/">Physics Site</a>

			<!-- Toggler for collapse -->
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle Navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<!-- Navbar Content (Collapsable) -->
			<div class="collapse navbar-collapse" id="navbarContent">
				<ul class="navbar-nav container-fluid">

					<!-- User Buttons -->
					<li class="nav-item ms-auto">
						<a class="nav-link" href="/login">Login</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/register">Register</a>
					</li>

				</ul>
			</div>

		</div>
	</nav>
</template>

<script>
export default {
	name: 'Navbar'
}
</script>

<style scoped>
#navbar {
	background-color: rgb(255, 140, 0);
}
</style>