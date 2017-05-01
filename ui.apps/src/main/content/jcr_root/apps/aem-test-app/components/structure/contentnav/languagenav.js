use(function () {
    var items = [];
    console.log('root path');
    var root = currentPage.getAbsoluteParent(1);
    console.log(root);
    var currentNavPath = currentPage.getAbsoluteParent(2).getPath();
    var it = root.listChildren(new Packages.com.day.cq.wcm.api.PageFilter());

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