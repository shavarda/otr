module.exports = {
  context: __dirname + '/src',
  entry: './main/resources/static/js/index.js',
  output: {
    path: __dirname + '/src',
    filename: './main/resources/static/js/bundle.js'
  },
  module: {
    loaders: [
      {test: /\.js$/, loader: 'babel'},
      {test: /\.html$/, loader: 'raw'}
    ]
  }
};