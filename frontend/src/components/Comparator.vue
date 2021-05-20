<template>
  <div>
    <span>
      <img
        class="attacker card"
        v-if="comparator.attacker !== null"
        v-bind:src="
          getImagePath(
            comparator.attacker.card.suit,
            comparator.attacker.card.value
          )
        "
        alt="attacker's card"
      />
      <img
        class="defender card"
        v-if="comparator.defender !== null"
        v-bind:src="
          getImagePath(
            comparator.defender.card.suit,
            comparator.defender.card.value
          )
        "
        alt="defender's card"
      />
    </span>
  </div>
</template>

<script>
import gsap from 'gsap'
import imageMixin from '@/mixins/imageMixin'

export default {
  name: 'Comparator',
  mixins: [imageMixin],
  props: {
    comparator: {
      required: true,
    },
  },
  updated() {
    gsap.to('.attacker', {
      duration: 0.7,
      opacity: 1,
      x: 10,
      ease: 'easeOutElastic',
      stagger: {
        each: 0.1,
        from: 0,
      },
    })
    gsap.fromTo(
      '.defender',
      { x: 20 },
      {
        duration: 0.7,
        opacity: 1,
        x: 10,
        ease: 'easeOutElastic',
        stagger: {
          each: 0.1,
          from: 0,
        },
      }
    )
  },
}
</script>
