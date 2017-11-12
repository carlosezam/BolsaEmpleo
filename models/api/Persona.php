<?php

namespace app\models\api;

use Yii;

/**
 * This is the model class for table "personas".
 *
 * @property integer $id
 * @property string $curp
 * @property string $nombres
 * @property string $ape_pat
 * @property string $ape_mat
 * @property string $fecha_nac
 * @property string $sexo
 * @property string $edo_civil
 * @property string $telefono
 * @property string $domicilio
 * @property integer $id_usuario
 *
 * @property Usuarios $idUsuario
 */
class Persona extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'personas';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [
                [
                    'curp', 'nombres', 'ape_pat', 'ape_mat', 'fecha_nac',
                    'sexo', 'edo_civil', 'telefono', 'domicilio', 'licencia' ,'id_usuario'
                ],
                'required', 'message' => 'Campo requerido'
            ],
            [
                [
                    'curp', 'nombres', 'ape_pat', 'ape_mat', 'fecha_nac',
                    'sexo', 'edo_civil', 'telefono', 'domicilio', 'licencia' ,'id_usuario'
                ],
                'trim'
            ],
            [
                ['fecha_nac'],
                'date', 'format' => 'yyyy-MM-dd','message' => 'Formato de fecha no valido'
            ],
            [
                ['sexo'], 'in', 'range' => ['M','F'], 'message' => 'Sexo no valido'
            ],
            [
                ['telefono', 'id_usuario'],
                'integer'
            ],
            [
                ['curp'], 'string', 'max' => 18
            ],
            [
                ['nombres'], 'string', 'max' => 40
            ],
            [
                ['ape_pat', 'ape_mat'], 'string', 'max' => 20
            ],
            [
                ['edo_civil'],
                    'in', 'range' => ['casado(a)','soltero(a)'], 'message' => 'Estado civil invalido'
            ],
            [
                ['licencia'],
                    'boolean', 'message' => 'El valor debe ser booleano'
            ],
            [
                ['domicilio'], 'string', 'max' => 150
            ],
            [
                ['curp'], 'unique', 'message' => 'Ya existe un usuario registrado con esta curp'
            ],
            [
                ['id_usuario'],
                    'exist', 'skipOnError' => true,
                        'targetClass' => Usuarios::className(), 'targetAttribute' => ['id_usuario' => 'id']
            ],
            [
                ['id_usuario'],
                    'unique', 'message' => 'Ya existe un registro para este usuario'
            ]
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'curp' => 'Curp',
            'nombres' => 'Nombres',
            'ape_pat' => 'Ape Pat',
            'ape_mat' => 'Ape Mat',
            'fecha_nac' => 'Fecha Nac',
            'sexo' => 'Sexo',
            'edo_civil' => 'Edo Civil',
            'telefono' => 'Telefono',
            'domicilio' => 'Domicilio',
            'id_usuario' => 'Id Usuario',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getIdUsuario()
    {
        return $this->hasOne(Usuarios::className(), ['id' => 'id_usuario']);
    }
}
