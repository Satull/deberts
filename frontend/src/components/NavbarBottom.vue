<template>
  <div class="font-bold font-sans select-none text-l">
    <p hidden>{{ roundTurn }}</p>
    <transition
      @before-enter="beforeEnter"
      @enter="enter"
      @leave="leave"
      :css="false"
    >
      <div v-if="isOpen" class="history">
        <table class="tablespace">
          <tr
            class=""
            v-for="(roundService, suitIndex) in roundHistory"
            :key="suitIndex"
          >
            <td class="cell">{{ roundService.bot }}</td>
            <td class="cell">
              {{ roundService.player }}
            </td>
          </tr>
        </table>
      </div>
    </transition>
    <div class="taskbar">
      <a class="player">Player: {{ player.points }}</a>
      <a class="menu" v-on:click="changeOpen()">History</a>
      <a class="menu" v-on:click="reset()">Reset</a>
      <a class="menu" v-on:click="save()">Save</a>
      <a class="menu" v-on:click="load()">Load</a>

      <a class="space"></a>
      <div
        class="tradebot"
        v-if="
          roundTurn === 'player' &&
          trumpSuit !== undefined &&
          trumpSuit !== null &&
          passes < 5 &&
          phase === 6
        "
      >
        <a v-if="passes < 4" class="menu menu-left" v-on:click="pass()">Pass</a>
        <a v-if="passes === 4" class="space"> </a>
        <a
          v-if="passes < 2"
          class="menu menu-left"
          v-on:click="playTrump(trumpSuit)"
          >Play</a
        >

        <div>
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
          <a class="right-border menu-left"></a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DebertsService from '@/services/DebertsService.js'
import Velocity from 'velocity-animate'
import gsap from 'gsap'

export default {
  name: 'NavbarBottom',
  data() {
    return {
      isOpen: false,
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
    player: {
      required: true,
    },
    roundHistory: {
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
  },
  methods: {
    changeOpen() {
      this.isOpen = !this.isOpen
      console.log(this.isOpen)
    },
    beforeEnter(el) {
      el.style.opacity = '0'
      el.style.width = '0'
      el.style.left = '1500px'
    },
    enter(el, done) {
      Velocity(
        el,
        { opacity: 1, width: '231px', left: '1202px' },
        { duration: 1000, easing: [600, 50], complete: done }
      )
    },
    leave(el, done) {
      Velocity(
        el,
        { opacity: 0, width: '0em', left: '1500px' },
        { duration: 500, easing: 'easeInCubic', complete: done }
      )
    },
    pass() {
      if (this.roundTurn === 'player') {
        this.$emit('update:turn')
        this.$store.dispatch('addPasses')
      }
    },
    playTrump(trump) {
      if (this.roundTurn === 'player') {
        DebertsService.playTrump('player', trump).then((response) => {
          if (response.status === 200) {
            this.$store.dispatch('setPasses', 5)
            this.$emit('update:partyService')
          }
        })
      }
    },
    reset() {
      DebertsService.reset().then((response) => {
        if (response.status === 200) {
          this.$store.dispatch('setPasses', 0)
          this.$emit('update:partyService')
        }
      })
    },
    save() {
      DebertsService.save()
    },
    load() {
      DebertsService.load().then((response) => {
        if (response.status === 200) {
          this.$emit('update:partyService')
        }
      })
    },
    setCSSOnTurn(roundTurn) {
      roundTurn === 'player'
        ? (this.turnCSS =
            'hover:bg-gray-300 hover:text-green-700 cursor-pointer')
        : (this.turnCSS = '')
    },
  },
  updated() {
    this.setCSSOnTurn(this.roundTurn)
    gsap.fromTo(
      '.tradebot',
      { y: 100, opacity: 0 },
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
.history {
  position: absolute;
  top: 50px;
  float: right;
  right: 0px;
  margin-left: 8px;
}

.cell {
  background-color: #036140;
  border-left: 3px;
  border-color: white;
  border-bottom: 2px solid white;
  border-left: 2px solid white;

  color: #f2f2f2;
  text-align: center;
  padding: 14px 45px;
  text-decoration: none;
  font-size: 17px;
  width: 80px;
  cursor: default;
}

.taskbar {
  background-color: #333;
  padding-left: 0;
  overflow: hidden;
  border-top: 2px solid white;
  position: fixed;
  bottom: 0;
  width: 100%;
  -webkit-touch-callout: none; /* iOS Safari */
  -webkit-user-select: none; /* Safari */
  -khtml-user-select: none; /* Konqueror HTML */
  -moz-user-select: none; /* Old versions of Firefox */
  -ms-user-select: none; /* Internet Explorer/Edge */
  user-select: none;
}

.taskbar a.menu {
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

.taskbar a.right-border {
  float: right;
  background-color: #610319;
  color: #f2f2f2;
  text-align: center;
  padding: 0px 0px;
  height: 48px;
  text-decoration: none;
  font-size: 17px;
  border-right: 2px solid white;
  cursor: pointer;
}

.taskbar a.menu-left {
  float: left;
}

.taskbar a.menu:hover {
  background-color: #ddd;
  color: black;
}

.taskbar a.player {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
  background-color: #073a76;
  width: 80px;
  border-right: 2px solid white;
  cursor: default;
}

.taskbar a.space {
  float: left;
  width: 80px;
  padding: 14px 16px;
}
</style>
