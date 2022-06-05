const {Sequelize, DataTypes, Model} = require('sequelize')
const { sequelize } = require('./db')

const User = sequelize.define('User',{
    id:{
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    pseudo:{
        type: DataTypes.STRING,
        allowNull: false
    },
    email:{
        type: DataTypes.STRING,
        allowNull: false
    },
    password: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    roles:{
        type:DataTypes.STRING,
        allowNull: false
    },
})

module.exports = User