module.exports = {
    // proxy all webpack dev-server requests starting with /api
    // see https://cli.vuejs.org/config/#devserver-proxy
    devServer: {
      proxy: {
        '/api': {
          target: 'http://localhost:8080', 
          ws: true,
          changeOrigin: true
        }
      }
    },
    // Change build paths to make them Maven compatible
    // see https://cli.vuejs.org/config/
    outputDir: 'target/dist',
    assetsDir: 'static'
  };