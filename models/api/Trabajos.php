<?php

namespace app\models\api;

use app\models\Usuario;
use Yii;

/**
 * This is the model class for table "trabajos".
 *
 * @property integer $id
 * @property string $empresa
 * @property string $puesto
 * @property string $actividades
 * @property string $fecha_ini
 * @property string $fecha_fin
 * @property integer $id_usuario
 *
 * @property Usuarios $idUsuario
 */
class Trabajos extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'trabajos';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['empresa', 'puesto', 'actividades', 'fecha_ini', 'fecha_fin', 'id_usuario'],
                'required', 'message' => 'Campo {attribute} requerido'],
            [['fecha_ini', 'fecha_fin'], 'date', 'format' => 'yyyy-MM-dd','message' => 'Ingrese la fecha en formato AAAA-MM-DD'],
            [['id_usuario'], 'integer'],
            [['empresa', 'puesto'], 'string',
                'length' => [5,150],
                'tooShort' => 'Al menos 5 caracteres',
                'tooLong' => 'Maximo 150 caracteres'],
            [['actividades'], 'string',
                'length' => [5,300],
                'tooShort' => 'Al menos 5 caracteres',
                'tooLong' => 'Maximo 50 caracteres'],
            [['id_usuario'], 'exist', 'skipOnError' => true, 'targetClass' => Usuario::className(), 'targetAttribute' => ['id_usuario' => 'id']],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'empresa' => 'Empresa',
            'puesto' => 'Puesto',
            'actividades' => 'Actividades',
            'fecha_ini' => 'Fecha Ini',
            'fecha_fin' => 'Fecha Fin',
            'id_usuario' => 'Id Usuario',
        ];
    }

    public static function curriculumFields()
    {
        return [
            'empresa' => 'Empresa',
            'puesto' => 'Puesto',
            'actividades' => 'Actividades',
            'fecha_ini' => 'Fecha Inicio',
            'fecha_fin' => 'Fecha Termino'
        ];
    }
    /**
     * @return \yii\db\ActiveQuery
     */
    public function getIdUsuario()
    {
        return $this->hasOne(Usuario::className(), ['id' => 'id_usuario']);
    }
}
