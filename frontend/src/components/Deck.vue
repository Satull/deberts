<template>
  <div>
    <span class="inline-flex">
      <img
        class="carddeck h-48 m-3"
        @click="
          deckCards > 19 || player.cards + bot.cards === 0
            ? switchPhase()
            : null
        "
        src="@/assets/images/cardBack.png"
        alt="Image of the card deck"
      />
      <img
        v-if="
          (trump !== undefined && trump.suit !== null && phase === 6) ||
            phase === 3
        "
        class="trump h-48 m-3"
        v-bind:src="getImagePath(trump.suit, trump.card)"
        @click="
          deckCards === 13 && switchAllowed == true ? switchTrump() : null
        "
        alt="trump"
      />
    </span>
  </div>
</template>

<script>
import DebertsService from '@/services/DebertsService.js'
import imageMixin from '@/mixins/imageMixin'
import gsap from 'gsap'

export default {
  name: 'Deck',
  mixins: [imageMixin],
  props: {
    bot: {
      required: true
    },
    deckCards: {
      type: Number,
      required: true
    },
    player: {
      required: true
    },
    phase: {
      type: Number,
      required: true
    },
    switchAllowed: {
      type: Boolean,
      required: true
    },
    trump: {
      type: Object,
      required: true
    }
  },
  beforeMount() {
    this.$emit('update:party')
  },
  mounted() {
    gsap.config({ nullTargetWarn: false })
    gsap.fromTo(
      '.trump',
      { x: '20px', opacity: 0 },
      {
        duration: 0.9,
        opacity: 1,
        x: 0,
        ease: 'easeOutElastic',
        stagger: {
          each: 0.1,
          from: 0
        }
      }
    )
    gsap.fromTo(
      '.carddeck',
      { opacity: 0, x: '-20px' },
      {
        duration: 0.9,
        x: 0,
        opacity: 1,
        ease: 'easeOutElastic',
        stagger: {
          each: 0.1,
          from: 0
        }
      }
    )
  },
  methods: {
    switchPhase() {
      DebertsService.switchPhase().then(response => {
        if (response.status === 200) {
          this.$store.dispatch('setPasses', 0)
          this.$emit('update:party')
        }
      })
    },
    switchTrump() {
      DebertsService.switchTrump().then(response => {
        if (response.status === 200) {
          this.$emit('update:party')
        }
      })
    }
  },
  updated() {
    gsap.fromTo(
      '.trump',
      { x: '20px', opacity: 0 },
      {
        duration: 0.9,
        opacity: 1,
        x: 0,
        ease: 'easeOutElastic',
        stagger: {
          each: 0.1,
          from: 0
        }
      }
    )
  }
}
</script>
