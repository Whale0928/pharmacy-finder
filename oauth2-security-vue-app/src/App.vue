<script setup>
</script>

<template>
  <header>
  </header>
  <router-view/> <!-- Add this line -->
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
