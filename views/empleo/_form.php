<?php

use yii\helpers\Html;
use yii\bootstrap\ActiveForm;

/* @var $this yii\web\View */
/* @var $model app\models\Empleo */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="empleo-form">
<br>

    <?php

    $form = ActiveForm::begin();

    ?>

    <?= $form->field($model, 'puesto')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'salario')->textInput() ?>

    <?= $form->field($model, 'descripcion')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'vacantes')->textInput() ?>

    <?= $form->field($model, 'domicilio')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'id_empresa')->dropdownList(\app\models\Empresa::dropdown()) ?>



    <div class="form-group">

        <?= Html::submitButton($model->isNewRecord ? Yii::t('app', 'Guardar') : Yii::t('app', 'Guardar'), ['class' => $model->isNewRecord ? 'btn btn-success' : 'btn btn-primary']) ?>

        <?php if(isset($mode) == 'update'):?>
            <?php echo Html::a(Yii::t('app', 'Cancelar'), ['index'], ['class' => 'btn btn-danger']) ?>
        <?php else: ?>
            <button type="button" class="btn btn-danger" data-dismiss="modal" aria-hidden="true">Cancelar</button>
        <?php endif ?>

    </div>
    <?php ActiveForm::end(); ?>

</div>


