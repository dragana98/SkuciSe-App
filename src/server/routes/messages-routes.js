const express = require('express');
const Messages = require('../models/Messages');
const router = express.Router();

router.post('/push', (req, res) => {
    var data = req.body;
    data.username = req.decodedToken.username;

    var { urcv, contents } = data

    if (!(urcv  && contents)) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Messages.push(data)
            .then(avdate => {
                res.status(200).json(avdate);
            })
            .catch(err => {
                console.log(err);
                res.status(500).json(err);
            })
    }
});

router.post('/markAsRead', (req, res) => {
    var data = req.body;
    var { usnd } = data;

    data.username = req.decodedToken.username;

    if (!usnd) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Messages.markConversationAsRead(data)
            .then(avdate => {
                res.status(200).json(avdate);
            })
            .catch(err => {
                console.log(err);
                res.status(500).json(err);
            })
    }
});

router.get('/preview/:uid', (req, res) => {
    const { uid } = req.params;
    Messages.preview(uid)
        .then(avdate => {
            res.status(200).json(avdate)
        })
        .catch(error => {
            res.status(500).json({ message: "Server Error" })
        })
});

router.get('/:uid', (req, res) => {
    const { uid } = req.params;
    Messages.readAll(uid)
        .then(avdate => {
            res.status(200).json(avdate)
        })
        .catch(error => {
            res.status(500).json({ message: "Server Error" })
        })
});


module.exports = router;