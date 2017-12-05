<?php

use yii\widgets\DetailView;

/* @var $this yii\web\View */
/* @var $model app\models\api\Empleos */
?>
<div class="empleos-view">
 
    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            'puesto',
            'salario',
            'descripcion',
            'vacantes',
            'domicilio',
            'id_empresa',
            'id_municipio',
            'active',
        ],
    ]) ?>

</div>
