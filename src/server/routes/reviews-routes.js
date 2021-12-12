const express = require('express');
const Reviews = require('../models/Reviews');
const router = express.Router();

router.get('/realtyReviews/:data', (req, res) => {
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

router.get('/userReviews/:data', (req, res) => {
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
    var { property_ad_id, date, stars, contents} = data

    data.response = null
    data.response_date = null

    data.username = req.decodedToken.username;

    if (!( property_ad_id && date && stars && contents)) {
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
    var { user_id, date, recommends, contents } = data

    data.username = req.decodedToken.username;

    if (!(user_id && date && recommends && contents)) {
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

module.exports = router;