<#import "parts/common.ftl" as c>

<@c.page>

    <form method="post">

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Название:</label>
            <div class="col-sm-6">
                <input type="text"
                       name="title"
                       class="form-control"
                       placeholder="Название файла" />
            </div>
        </div>

        <div class="form-group row">
            <label for="exampleFormControlFile1" class="col-sm-2 col-form-label">Файл для загрузки</label>
            <div class="col-sm-6">
                <input type="file" class="form-control-file" id="exampleFormControlFile1" name="file">
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <div>
            <button type="submit" class="btn btn-primary">Добавить</button>
        </div>
    </form>

</@c.page>