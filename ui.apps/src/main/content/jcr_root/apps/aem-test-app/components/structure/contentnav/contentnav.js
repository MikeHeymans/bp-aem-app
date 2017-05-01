use(function () {
    var items = [];
    console.log('language path');
    var languagePath = currentPage.getAbsoluteParent(2);
    console.log(languagePath);
    console.log('currentNavPath lang');
    console.log(currentPage.getAbsoluteParent(3));
    var currentNavPath = currentPage.getAbsoluteParent(3).getPath();
    var it = languagePath.listChildren(new Packages.com.day.cq.wcm.api.PageFilter());

    while (it.hasNext()) {
        var page = it.next();
        var selected = (page.getPath() == currentNavPath);
        items.push({
            page: page,
            selected : selected
        });
    }
    return {
        items: items
    };
});