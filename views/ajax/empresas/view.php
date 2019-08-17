<?php

use yii\widgets\DetailView;

/* @var $this yii\web\View */
/* @var $model app\models\ajax\Empresas */
?>
<div class="empresas-view">
 
    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            'rfc',
            'nombre',
            'telefono',
            'correo',
            'encargado',
            'domicilio',
        ],
    ]) ?>

</div>
