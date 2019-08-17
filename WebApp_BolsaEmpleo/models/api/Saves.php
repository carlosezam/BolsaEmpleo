<?php

namespace app\models\api;

use Yii;

/**
 * This is the model class for table "saves".
 *
 * @property integer $id
 * @property integer $id_empleo
 * @property integer $id_usuario
 *
 * @property Usuarios $idUsuario
 * @property Empleos $idEmpleo
 */
class Saves extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'saves';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['id_empleo', 'id_usuario'], 'required'],
            [['id_empleo', 'id_usuario'], 'integer'],
            [['id_usuario'], 'exist', 'skipOnError' => true, 'targetClass' => Usuarios::className(), 'targetAttribute' => ['id_usuario' => 'id']],
            [['id_empleo'], 'exist', 'skipOnError' => true, 'targetClass' => Empleos::className(), 'targetAttribute' => ['id_empleo' => 'id']],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'id_empleo' => 'Id Empleo',
            'id_usuario' => 'Id Usuario',
        ];
    }

    public function fields()
    {
        return [
            'id' => 'id',
            'id_empleo' => 'id_empleo',
            'empleo' => function() { return $this->empleo->puesto; },
            'empresa' => function() {
                $empleo = $this->empleo;
                $empresa = $empleo->empresa;
            return $empresa->nombre; }
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getIdUsuario()
    {
        return $this->hasOne(Usuarios::className(), ['id' => 'id_usuario']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getEmpleo()
    {
        return $this->hasOne(Empleos::className(), ['id' => 'id_empleo']);
    }
}
