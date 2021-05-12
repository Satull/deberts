export default {
  methods: {
    getImagePath(suit, card) {
      return require('@/assets/images/' + suit + '/' + suit + card + '.png')
    },
  },
}
