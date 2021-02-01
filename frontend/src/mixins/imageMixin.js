import DebertsService from '@/services/DebertsService.js'

export default {
  methods: {
    getImagePath(suit, card) {
      return require('@/assets/images/' + suit + '/' + suit + card + '.png')
    }
  }
}
