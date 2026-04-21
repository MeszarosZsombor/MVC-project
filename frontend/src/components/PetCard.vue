<template>
  <div class="pet-card">
    <div class="pet-card__header">
      <div class="pet-card__avatar" :class="genderClass">
        {{ initials }}
      </div>
      <div class="pet-card__badges">
        <span class="badge" :class="genderClass">{{ pet.gender }}</span>
        <span class="badge" :class="pet.adopted ? 'badge--adopted' : 'badge--available'">
          {{ pet.adopted ? 'Adopted' : 'Available' }}
        </span>
      </div>
    </div>

    <div class="pet-card__body">
      <h3 class="pet-card__name">{{ pet.petName }}</h3>
      <div class="pet-card__stats">
        <div class="stat">
          <span class="stat__label">Age</span>
          <span class="stat__value">{{ pet.age }} yrs</span>
        </div>
        <div class="stat">
          <span class="stat__label">Weight</span>
          <span class="stat__value">{{ pet.weight }} kg</span>
        </div>
        <div class="stat">
          <span class="stat__label">ID</span>
          <span class="stat__value">#{{ pet.petId }}</span>
        </div>
      </div>
    </div>

    <div class="pet-card__footer">
      <span class="pet-card__date">Added {{ formattedDate }}</span>
      <slot name="actions" />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  pet: {
    type: Object,
    required: true
  }
});

const initials = computed(() => {
  return props.pet.petName
      ? props.pet.petName.slice(0, 2).toUpperCase()
      : '??';
});

const genderClass = computed(() => {
  return props.pet.gender === 'male' ? 'gender--male' : 'gender--female';
});

const formattedDate = computed(() => {
  if (!props.pet.createdAt) return '—';
  return new Date(props.pet.createdAt).toLocaleDateString('en-GB', {
    day: 'numeric', month: 'short', year: 'numeric'
  });
});
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500&display=swap');

.pet-card {
  font-family: 'DM Sans', sans-serif;
  background: white;
  border: 0.5px solid #e5e5e5;
  border-radius: 12px;
  padding: 1.25rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  transition: box-shadow 0.2s, transform 0.2s;
}

.pet-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.07);
}

/* Header */
.pet-card__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.pet-card__avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: 'DM Serif Display', serif;
  font-size: 16px;
  font-weight: 400;
  flex-shrink: 0;
}

.pet-card__avatar.gender--male {
  background: #E6F1FB;
  color: #185FA5;
}

.pet-card__avatar.gender--female {
  background: #FBEAF0;
  color: #993556;
}

.pet-card__badges {
  display: flex;
  gap: 6px;
  flex-wrap: wrap;
  justify-content: flex-end;
}

/* Badges */
.badge {
  font-size: 11px;
  font-weight: 500;
  padding: 3px 10px;
  border-radius: 20px;
  text-transform: capitalize;
  letter-spacing: 0.03em;
}

.badge.gender--male {
  background: #E6F1FB;
  color: #185FA5;
}

.badge.gender--female {
  background: #FBEAF0;
  color: #993556;
}

.badge--available {
  background: #E1F5EE;
  color: #0F6E56;
}

.badge--adopted {
  background: #F1EFE8;
  color: #5F5E5A;
}

/* Body */
.pet-card__body {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.pet-card__name {
  font-family: 'DM Serif Display', serif;
  font-size: 20px;
  font-weight: 400;
  color: #1a1a1a;
  margin: 0;
  letter-spacing: -0.3px;
}

.pet-card__stats {
  display: flex;
  gap: 0;
  border: 0.5px solid #e5e5e5;
  border-radius: 8px;
  overflow: hidden;
}

.stat {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 8px 4px;
  gap: 2px;
}

.stat + .stat {
  border-left: 0.5px solid #e5e5e5;
}

.stat__label {
  font-size: 11px;
  color: #aaa;
  font-weight: 400;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.stat__value {
  font-size: 14px;
  font-weight: 500;
  color: #1a1a1a;
}

/* Footer */
.pet-card__footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: 0.75rem;
  border-top: 0.5px solid #f0f0f0;
}

.pet-card__date {
  font-size: 12px;
  color: #bbb;
  font-weight: 300;
}
</style>