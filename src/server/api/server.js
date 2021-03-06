const express = require('express');
const authRouter = require("../auth/auth-routes");
const restricted = require("../auth/restricted-middleware");

const server = express();

server.use(express.json({limit: '50mb'}));
server.use(express.urlencoded({limit: '50mb', extended: true}));

const usersRouter = require("../routes/users-routes");
const listingsRouter = require("../routes/listings-routes");
const realtorsRouter = require("../routes/realtors-routes");
const favoritesRouter = require("../routes/favorites-routes");
const tourDatesRouter = require("../routes/tourdates-routes");
const reviewsRouter = require("../routes/reviews-routes");
const messagesRouter = require("../routes/messages-routes");
const fileUploadRouter = require("../routes/fileupload-routes");

server.get('/', (req, res) => {
    res.json({ message: 'I am son of Hal and am always watching!'})
});

server.use('/api/users', restricted, usersRouter);
server.use('/api/listings', restricted, listingsRouter);
server.use('/api/realtors', restricted, realtorsRouter);
server.use('/api/favorites', restricted, favoritesRouter);
server.use('/api/tourDates', restricted, tourDatesRouter);
server.use('/api/reviews', restricted, reviewsRouter);
server.use('/api/messages', restricted, messagesRouter);
server.use('/files/', fileUploadRouter);

server.use('/api/auth', authRouter);

module.exports = server;
