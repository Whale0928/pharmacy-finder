<script setup>
import LoginForm from "@/components/loginForm.vue";
</script>

<template>
  <header>
    <div>
      <LoginForm/>
    </div>
  </header>

</template>

<style scoped>
header {
  line-height: 1.5;
  padding: var(--section-gap);
}

@media (min-width: 1024px) {
  header {
    display: flex;
    justify-content: center;
  }
}
</style>
