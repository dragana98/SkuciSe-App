const jwt = require('jsonwebtoken')
module.exports = (req, res, next) => {
    const token = req.headers.authorization
    const secret = process.env.SECRET

    if(token){
        jwt.verify(token, secret, (err, decoded) => {
            if(err){
                res.status(401).json({ message: "Invalid token received" })
            }else{
                req.decodedToken = decoded;
                next();
            }
        })
    }else{
        res.status(401).json({ message: "No token received" });
    }
};