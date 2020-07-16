export default ngModule => {
  require('./menu/menu.js')(ngModule);
  require('./list-organization/list-organization.js')(ngModule);
  require('./list-worker/list-worker.js')(ngModule);
  require('./add-organization/add-organization.js')(ngModule);
  require('./add-worker/add-worker.js')(ngModule);
  require('./edit-organization/edit-organization.js')(ngModule);
  require('./edit-worker/edit-worker.js')(ngModule);
  require('./tree-organization/tree-organization.js')(ngModule);
  require('./tree-worker/tree-worker.js')(ngModule);
}