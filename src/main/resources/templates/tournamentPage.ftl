<#import "parts/common.ftl" as c>

<@c.page>

    <form method="post">

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Команда 1:</label>
            <div class="col-sm-6">
                <select class="form-control" name="league">
                    <option value="" selected disabled>Выбор игрока</option>
                    <option>Англия: АПЛ</option>
                    <option>Испания: Ла Лига</option>
                    <option>Италия: Серия А</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Команда 2:</label>
            <div class="col-sm-6">
                <select class="form-control" name="league">
                    <option value="" selected disabled>Выбор игрока</option>
                    <option>Англия: АПЛ</option>
                    <option>Испания: Ла Лига</option>
                    <option>Италия: Серия А</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Команда 3:</label>
            <div class="col-sm-6">
                <select class="form-control" name="league">
                    <option value="" selected disabled>Выбор игрока</option>
                    <option>Англия: АПЛ</option>
                    <option>Испания: Ла Лига</option>
                    <option>Италия: Серия А</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Команда 4:</label>
            <div class="col-sm-6">
                <select class="form-control" name="league">
                    <option value="" selected disabled>Выбор игрока</option>
                    <option>Англия: АПЛ</option>
                    <option>Испания: Ла Лига</option>
                    <option>Италия: Серия А</option>
                </select>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div>
            <button type="submit" class="btn btn-primary">Подобрать команду</button>
        </div>

    </form>

</@c.page>