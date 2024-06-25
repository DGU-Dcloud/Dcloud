const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://210.94.179.19:9497',	// 서버 URL or localhost:설정한포트번호
      changeOrigin: true,
    })
  );
};