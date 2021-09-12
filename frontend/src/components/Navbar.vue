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
					<li class="ms-md-auto"></li>
					<li class="nav-item" v-if="!isLoggedIn">
						<a class="nav-link" href="/login">Login</a>
					</li>
					<li class="nav-item" v-if="!isLoggedIn">
						<a class="nav-link" href="/register">Register</a>
					</li>
					<li class="nav-item" v-if="isLoggedIn && userData.auths.includes('ROLE_ADMIN')">
						<a class="nav-link" href="/admin">Admin</a>
					</li>
					<li class="nav-item" v-if="isLoggedIn">
						<a class="nav-link" href="/account">Account</a>
					</li>
					<li class="nav-item" v-if="isLoggedIn">
						<a class="nav-link" @click="logout()">Logout</a>
					</li>

				</ul>
			</div>

		</div>
	</nav>
</template>

<script>
import $ from 'jquery';

export default {
	name: 'Navbar',

	beforeMount() {
		this.checkLoginStatus();
	},

	data() {
		return {
			isLoggedIn: false,
			userData: null
		}
	},

	methods: {
		checkLoginStatus() {
			$.get(
				"/user/data",
				(data) => {
					if(!data.isError) {
						this.isLoggedIn = true;
						this.userData = {
							username: data.username,
							email: data.email,
							auths: data.auths
						}
					}
				}
			);
		},

		logout() {
			$.post(
				"/logout",
				() => {
					window.location.replace("/");
				}
			);
		}
	}
}
</script>

<style scoped>
#navbar {
	background-color: var(--primary-color);
}

.nav-link {
	font-weight: 500;
}
</style>