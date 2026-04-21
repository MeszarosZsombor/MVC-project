<script setup>
  import Logo from "./Logo.vue";
  import { useRoute } from 'vue-router';
  import { computed, ref, watch, nextTick } from 'vue';

  const route = useRoute();
  const buttonRefs = [];  // sima tömb, nem ref()
  const collapsedWidth = ref('160px');

  const navItems = [
    { to: '/users', label: 'Users' },
    { to: '/pet-categories', label: 'Pet Categories' },
    { to: '/pets', label: 'Pets' },
    { to: '/adoptions', label: 'Adoptions' },
  ];

  const sortedNavItems = computed(() => {
    const active = navItems.find(item => route.path.startsWith(item.to));
    const rest = navItems.filter(item => item !== active);
    return active ? [active, ...rest] : navItems;
  });

  watch(
      () => route.path,
      async () => {
        await nextTick();
        const activeEl = buttonRefs[0];
        if (activeEl) {
          const el = activeEl.$el ?? activeEl;
          collapsedWidth.value = (el.offsetWidth + 8) + 'px';
        }
      },
      { immediate: true }
  );
</script>

<template>
  <header>
    <Logo class="logo"></Logo>
    <nav class="nav" :style="{ '--collapsed-width': collapsedWidth }">
      <router-link
          v-for="(item, index) in sortedNavItems"
          :key="item.to"
          :to="item.to"
          class="site-button"
          :ref="el => { if (el) buttonRefs[index] = el }"
      >
        {{ item.label }}
      </router-link>
    </nav>
  </header>
</template>

<style scoped>
  @import url('https://fonts.googleapis.com/css2?family=DM+Sans:wght@300;400;500&display=swap');

  header {
    font-family: 'DM Sans', sans-serif;
    margin: 20px 20px 20px;
    display: flex;
    align-items: center;
    gap: 16px;
  }

  .logo {
    flex-shrink: 0;
  }

  .nav {
    display: flex;
    align-items: center;
    background: white;
    border: 0.5px solid #e5e5e5;
    border-radius: 10px;
    padding: 4px;
    gap: 2px;
    overflow: hidden;
    max-width: var(--collapsed-width, 160px);
    transition: max-width 1.5s cubic-bezier(0.4, 0, 0.2, 1);
  }

  .nav:hover {
    max-width: 600px;
  }

  .site-button {
    cursor: pointer;
    text-decoration: none;
    color: #555;
    padding: 6px 12px;
    border-radius: 7px;
    font-size: 14px;
    font-weight: 400;
    white-space: nowrap;
    transition: background 0.15s, color 0.15s;
  }

  .site-button:hover {
    background: #f7f7f5;
    color: #1a1a1a;
  }

  .site-button.router-link-active {
    background: #1D9E75;
    color: white;
    font-weight: 500;
  }
</style>