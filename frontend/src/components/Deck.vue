<template>
  <div>
    <span class="trumpdeck">
      <img
        class="deck"
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
        class="card"
        v-bind:src="getImagePath(trump.suit, trump.card)"
        @click="
          deckCards === 13 && switchAllowed === true ? switchTrump() : null
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
      required: true,
    },
    deckCards: {
      type: Number,
      required: true,
    },
    player: {
      required: true,
    },
    phase: {
      type: Number,
      required: true,
    },
    switchAllowed: {
      type: Boolean,
      required: true,
    },
    trump: {
      type: Object,
      required: true,
    },
  },
  beforeMount() {
    this.$emit('update:partyService')
  },
  mounted() {
    gsap.config({ nullTargetWarn: false })
    gsap.fromTo(
      '.card',
      { x: '20px', opacity: 0 },
      {
        duration: 0.9,
        opacity: 1,
        x: 0,
        ease: 'easeOutElastic',
      }
    )
    gsap.fromTo(
      '.deck',
      { opacity: 0, x: '-20px' },
      {
        duration: 0.9,
        x: 0,
        opacity: 1,
        ease: 'easeOutElastic',
      }
    )
  },
  methods: {
    switchPhase() {
      DebertsService.switchPhase().then((response) => {
        if (response.status === 200) {
          this.$store.dispatch('setPasses', 0)
          this.$emit('update:partyService')
        }
      })
    },
    switchTrump() {
      DebertsService.switchTrump().then((response) => {
        if (response.status === 200) {
          this.$emit('update:partyService')
        }
      })
    },
  },
  updated() {
    gsap.fromTo(
      '.card',
      { x: '20px', opacity: 0 },
      {
        duration: 0.9,
        opacity: 1,
        x: 0,
        ease: 'easeOutElastic',
      }
    )
  },
}
</script>

<style>
.deck {
  width: 150px;
  position: relative;
  padding: 2px;
}
.trumpdeck {
  position: absolute;
  top: 50%;
  left: 2%;
  transform: translate(0%, -50%);
}
</style>
