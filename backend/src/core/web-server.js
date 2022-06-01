const express = require('express');
const { initializeConfigMiddlewares, initializeErrorMiddlwares } = require('./middlewares');
const userRoutes = require('../controllers/user.routes');
const infosRoutes = require('../controllers/infos.routes')
const adminRoutes = require('../controllers/admin.routes')
const datingRoutes = require('../controllers/dating.routes')
const { sequelize } = require('../models/db');
const User = require('../models/user.model')
const bcrypt = require("bcryptjs");
var salt = bcrypt.genSaltSync(12)

class WebServer {
  app = undefined;
  port = 3000;
  server = undefined;

  constructor() {
    this.app = express();
    this.syncDb();

    initializeConfigMiddlewares(this.app);
    this._initializeRoutes();
    initializeErrorMiddlwares(this.app);
  }

  async syncDb() {
    await sequelize.sync();
  }

  start() {
    this.server = this.app.listen(this.port, () => {
      console.log(`Server listening on port ${this.port}`);
    });
  }

  stop() {
    this.server.close();
  }

  _initializeRoutes() {
    this.app.use('/users', userRoutes.initializeRoutes());
    this.app.use('/infos',infosRoutes.initializeRoutes());
    this.app.use('/loginAdmin',adminRoutes.initializeRoutes())
    this.app.use('/dating',datingRoutes.initializeRoutes())
  }
}

module.exports = WebServer;