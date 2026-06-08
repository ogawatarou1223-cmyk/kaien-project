//ShowAllData用　チェックボックス選ばずに更新・削除押したら警告

function validateSelection() {
    const checkboxes = document.querySelectorAll('input[name="selectedData"]');
    let isChecked = false;

    for (let i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            isChecked = true;
            break;
        }
    }

    if (!isChecked) {
        alert("商品を選択してください。");
        return false;
    }
    return true;
}
