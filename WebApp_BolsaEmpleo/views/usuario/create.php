<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model app\models\usuario */

$this->title = Yii::t('app', 'Crear Usuario');
$this->params['breadcrumbs'][] = ['label' => Yii::t('app', 'Usuarios'), 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="usuario-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
