<template>
  <div
    class="
      bg-gray-900
      border-b-2 border-gray-300
      fixed
      font-bold font-sans
      select-none
      text-gray-300 text-l
      top-0
      w-full
    "
  >
    <p hidden>{{ roundTurn }}</p>
    <a class="bg-red-700 border-r-2 float-left text-center py-4 w-32"
      >Bot: {{ bot.points }}</a
    >
    <a class="bg-gray-900 float-left py-4 text-center w-40"></a>
    <div
      class="tradetop"
      v-if="
        roundTurn === 'bot' &&
        trumpSuit !== undefined &&
        trumpSuit !== null &&
        passes < 5 &&
        phase === 6
      "
    >
      <a
        v-if="passes < 4"
        :class="
          'bg-green-700 border-l-2 border-r-2  float-left py-4 text-center w-32 ' +
          turnCSS
        "
        v-on:click="pass()"
        >Pass</a
      >
      <a
        v-if="passes === 4"
        class="bg-gray-900 border-r-2 float-left py-7 text-center w-32"
      ></a>
      <a
        v-if="passes < 2"
        :class="
          'bg-green-700 border-r-2 float-left py-4 text-center w-32 ' + turnCSS
        "
        v-on:click="playTrump(trumpSuit)"
        >Play</a
      >
      <a
        v-if="trumpSuit !== 'spades' && passes > 1 && passes < 5"
        :class="
          'bg-green-700 border-r-2 float-left py-4 text-center w-32 ' + turnCSS
        "
        v-on:click="playTrump('spades')"
        >Spades</a
      >
      <a
        v-if="trumpSuit !== 'clubs' && passes > 1 && passes < 5"
        :class="
          'bg-green-700 border-r-2 float-left py-4 text-center w-32 ' + turnCSS
        "
        v-on:click="playTrump('clubs')"
        >Clubs</a
      >
      <a
        v-if="trumpSuit !== 'diamonds' && passes > 1 && passes < 5"
        :class="
          'bg-green-700 border-r-2 float-left py-4 text-center w-32 ' + turnCSS
        "
        v-on:click="playTrump('diamonds')"
        >Diamonds</a
      >
      <a
        v-if="trumpSuit !== 'hearts' && passes > 1 && passes < 5"
        :class="
          'bg-green-700 border-r-2 float-left py-4 text-center w-32 ' + turnCSS
        "
        v-on:click="playTrump('hearts')"
        >Hearts</a
      >
    </div>

    <div class="float-right text-center">
      <a class="bg-green-700 border-l-2 float-left p-4 w-32">Score: </a>
      <a class="bg-red-700 border-l-2 float-left p-4 w-32"
        >Bot: {{ bot.score }}</a
      >

      <a class="bg-blue-700 border-l-2 float-left p-4 w-32"
        >Player: {{ player.score }}</a
      >
    </div>
  </div>
</template>

<script>
import DebertsService from '@/services/DebertsService.js'
import gsap from 'gsap'

export default {
  name: 'NavbarTop',
  data() {
    return {
      turnCSS: '',
    }
  },
  props: {
    passes: {
      type: Number,
      required: true,
    },
    phase: {
      type: Number,
      required: true,
    },
    bot: {
      required: true,
    },
    player: {
      required: true,
    },
    roundTurn: {
      required: true,
    },
    trumpSuit: {
      type: String,
    },
  },
  mounted() {
    this.setCSSOnTurn(this.roundTurn)
    gsap.fromTo(
      '.trade',
      { y: -100, opacity: 0 },
      {
        y: 0,
        duration: 0.4,
        opacity: 1,
        ease: 'easeOutElastic',
        stagger: {
          each: 0.1,
          from: 0,
        },
      }
    )
  },
  methods: {
    pass() {
      this.roundTurn === 'bot'
        ? this.$emit('update:turn') && this.$store.dispatch('addPasses')
        : null
    },
    playTrump(trump) {
      if (this.roundTurn === 'bot') {
        DebertsService.playTrump('bot', trump).then((response) => {
          if (response.status === 200) {
            this.$store.dispatch('setPasses', 5)
            this.$emit('update:partyService')
          }
        })
      }
    },
    setCSSOnTurn(roundTurn) {
      roundTurn === 'bot'
        ? (this.turnCSS =
            'hover:bg-gray-300 hover:text-green-700 cursor-pointer')
        : (this.turnCSS = '')
    },
  },
  updated() {
    this.setCSSOnTurn(this.roundTurn)
    gsap.fromTo(
      '.tradetop',
      { y: -100, opacity: 0 },
      {
        y: 0,
        duration: 0.4,
        opacity: 1,
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
