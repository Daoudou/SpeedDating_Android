const {Sequelize, DataTypes, Model} = require('sequelize')
const { sequelize } = require('./db')

const User = require('./user.model')
const Infos = require('./infos.model')

const Dating = sequelize.define('Dating',{
    id:{
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    idUserDating:{
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,

        references : {
            model : User,
            key: 'id'
        }
    },
    idInfosDating:{
        type : DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        references: {
            model : Infos,
            key : 'id'
        }
    },
    peopleAdding:{
      type: DataTypes.STRING,
      allowNull:false
    },
    dateDating:{
        type: DataTypes.DATE,
        allowNull: true
    },
    comment:{
        type:DataTypes.STRING,
        allowNull: true
    },
    note:{
        type:DataTypes.INTEGER,
        allowNull: false
    },
})

module.exports = Dating