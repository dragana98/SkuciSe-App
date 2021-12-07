const express = require('express');
const Messages = require('../models/Messages');
const router = express.Router();

router.post('/push', (req, res) => {
    const data = req.body;
    var { usnd, urcv, contents } = data

    if (!(usnd  && urcv  && contents)) {
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