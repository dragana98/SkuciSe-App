const express = require('express');
const path = require('path')
const usersRouter = require("../routes/users-routes");
const authRouter = require("../auth/auth-routes");
const restricted = require("../auth/restricted-middleware");
const realtiesRouter = require("../routes/realties-routes");

const server = express();

server.use(express.json());

server.use('/img', express.static(path.join(__dirname, '/../public/img')))

server.get('/', (req, res) => {
    res.json({ message: 'I am son of Hal and am always watching!'})
});

server.use('/api/users', restricted, usersRouter);
server.use('/api/auth', authRouter);
server.use('/api/realties', realtiesRouter);
module.exports = server;
