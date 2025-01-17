<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model app\models\Empleo */

$this->title = Yii::t('app', 'Create Empleo');
$this->params['breadcrumbs'][] = ['label' => Yii::t('app', 'Empleos'), 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="empleo-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
