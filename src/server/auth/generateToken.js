const jwt = require ('jsonwebtoken');

function generateToken(){

}

module.exports = user => {
    //3 THINGS FOR TOKEN (PAYLOAD, SECRET, OPTIONS)
    const payload = {
        id: user.id,
        username: user.username
        //add more non-confidential data
    }
    const secret = process.env.SECRET;
    const options = {
        expiresIn: '1d'
    }

    return jwt.sign(payload, secret, options)
};