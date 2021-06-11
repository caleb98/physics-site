<template>
	<nav id="navbar" class="navbar navbar-expand-lg navbar-light">
		<div class="container-fluid">
			<span class="navbar-brand mb-0 h1" href="#">Navbar</span>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto">

					<li class="nav-item">
						<a class="nav-link active" aria-current="page" href="#">Home</a>
					</li>
					
					<li class="nav-item">
						<a class="nav-link" href="#">Link</a>
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
	background-color: rgb(240, 120, 50);
	border-radius: 0px 0px 5px 5px;
}
</style>