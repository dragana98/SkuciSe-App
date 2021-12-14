const express = require('express');
const Reviews = require('../models/Reviews');
const router = express.Router();

router.get('/realty/:property_ad_id', (req, res) => {
    const { property_ad_id } = req.params;

    if (!property_ad_id) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Reviews.readUserToRealtyByPropertyId(property_ad_id)
            .then(rev => {
                res.status(200).json(rev)
            })
            .catch(error => {
                res.status(500).json({ message: "Server Error" })
            })
    }
});

router.get('/user/:user_id', (req, res) => {
    const { user_id } = req.params;

    if (!user_id) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Reviews.readRealtorToUserByUserId(user_id)
            .then(rev => {
                res.status(200).json(rev)
            })
            .catch(error => {
                res.status(500).json({ message: "Server Error" })
            })
    }
});

router.post('/userToRealty', (req, res) => {
    var data = req.body;
    var { property_ad_id, stars, contents} = data

    data.username = req.decodedToken.username;

    if (!( property_ad_id && stars && contents)) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Reviews.createUserToRealty(data)
            .then(rev => {
                res.status(200).json(rev);
            })
            .catch(err => {
                console.log(err);
                res.status(500).json(err);
            })
    }
});

router.post('/realtorToUser', (req, res) => {
    var data = req.body;
    var { user_id, recommends, contents } = data

    data.username = req.decodedToken.username;

    if (!(user_id && recommends && contents)) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Reviews.createRealtorToUser(data)
            .then(rev => {
                res.status(200).json(rev);
            })
            .catch(err => {
                console.log(err);
                res.status(500).json(err);
            })
    }
});

router.post('/realtorsResponse', (req, res) => {
    var data = req.body;
    var { id, response } = data

    data.username = req.decodedToken.username;

    if (!(id && response)) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Reviews.realtorsResponse(data)
            .then(rev => {
                res.status(200).json(rev);
            })
            .catch(err => {
                console.log(err);
                res.status(500).json(err);
            })
    }
});

module.exports = router;