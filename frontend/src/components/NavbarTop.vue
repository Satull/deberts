<template>
  <div class="navbar">
    <p hidden>{{ roundTurn }}</p>
    <a class="bot color-bot">Bot: {{ bot.points }}</a>

    <a class="score color-bot">Bot: {{ bot.score }}</a>
    <a class="score color-player">Player: {{ player.score }}</a>
    <a class="score">Score: </a>

    <a class="space"></a>
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
      <div>
        <a
          v-if="passes < 4"
          class="menu menu-left"
          :class="turnCSS"
          v-on:click="pass()"
          >Pass</a
        >
        <a
          v-if="passes < 2"
          class="menu menu-left"
          :class="turnCSS"
          v-on:click="playTrump(trumpSuit)"
          >Play</a
        >
        <a
          v-if="trumpSuit !== 'spades' && passes > 1 && passes < 5"
          class="menu menu-left"
          :class="turnCSS"
          v-on:click="playTrump('spades')"
          >Spades</a
        >
        <a
          v-if="trumpSuit !== 'clubs' && passes > 1 && passes < 5"
          class="menu menu-left"
          :class="turnCSS"
          v-on:click="playTrump('clubs')"
          >Clubs</a
        >
        <a
          v-if="trumpSuit !== 'diamonds' && passes > 1 && passes < 5"
          class="menu menu-left"
          :class="turnCSS"
          v-on:click="playTrump('diamonds')"
          >Diamonds</a
        >
        <a
          v-if="trumpSuit !== 'hearts' && passes > 1 && passes < 5"
          class="menu menu-left"
          :class="turnCSS"
          v-on:click="playTrump('hearts')"
          >Hearts</a
        >
        <a class="border right-border"></a>
      </div>
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
      if (this.roundTurn === 'bot') {
        this.$emit('update:turn')
        this.$store.dispatch('addPasses')
      }
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

<style>
.navbar {
  background-color: #333;
  overflow: hidden;
  border-bottom: 2px solid white;
  position: fixed;
  top: 0;
  width: 100%;
  -webkit-touch-callout: none; /* iOS Safari */
  -webkit-user-select: none; /* Safari */
  -moz-user-select: none; /* Old versions of Firefox */
  -ms-user-select: none; /* Internet Explorer/Edge */
  user-select: none;
  margin: 0;
}

.navbar a.menu {
  float: right;
  background-color: #036140;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  width: 60px;
  text-decoration: none;
  font-size: 17px;
  border-left: 2px solid white;
  cursor: pointer;
}

.navbar a.score {
  float: right;
  background-color: #036140;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  width: 80px;
  text-decoration: none;
  font-size: 17px;
  border-left: 2px solid white;
  cursor: default;
}

.navbar a.color-player {
  background-color: #073a76;
}

.navbar a.color-bot {
  background-color: #76071d;
}

.navbar a.menu-left {
  float: left;
}

.navbar a.menu:hover {
  background-color: #ddd;
  color: black;
}

.navbar a.bot {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
  width: 80px;
  border-right: 2px solid white;
  cursor: default;
}

.navbar a.right-border {
  border-right: 2px solid white;
}

.navbar a.space {
  float: left;
  width: 80px;
  padding: 14px 16px;
}

.navbar a.border {
  height: 100%;
  padding: 30px 0;
}
</style>
