// Generated from Dynabuffers.g4 by ANTLR 4.7.2
// jshint ignore: start
var antlr4 = require('antlr4/index');

// This class defines a complete generic visitor for a parse tree produced by DynabuffersParser.

function DynabuffersVisitor() {
	antlr4.tree.ParseTreeVisitor.call(this);
	return this;
}

DynabuffersVisitor.prototype = Object.create(antlr4.tree.ParseTreeVisitor.prototype);
DynabuffersVisitor.prototype.constructor = DynabuffersVisitor;

// Visit a parse tree produced by DynabuffersParser#compilation.
DynabuffersVisitor.prototype.visitCompilation = function(ctx) {
  return this.visitChildren(ctx);
};


// Visit a parse tree produced by DynabuffersParser#enumType.
DynabuffersVisitor.prototype.visitEnumType = function(ctx) {
  return this.visitChildren(ctx);
};


// Visit a parse tree produced by DynabuffersParser#classType.
DynabuffersVisitor.prototype.visitClassType = function(ctx) {
  return this.visitChildren(ctx);
};


// Visit a parse tree produced by DynabuffersParser#unionType.
DynabuffersVisitor.prototype.visitUnionType = function(ctx) {
  return this.visitChildren(ctx);
};


// Visit a parse tree produced by DynabuffersParser#fieldType.
DynabuffersVisitor.prototype.visitFieldType = function(ctx) {
  return this.visitChildren(ctx);
};


// Visit a parse tree produced by DynabuffersParser#dataType.
DynabuffersVisitor.prototype.visitDataType = function(ctx) {
  return this.visitChildren(ctx);
};


// Visit a parse tree produced by DynabuffersParser#arrayType.
DynabuffersVisitor.prototype.visitArrayType = function(ctx) {
  return this.visitChildren(ctx);
};


// Visit a parse tree produced by DynabuffersParser#classOptions.
DynabuffersVisitor.prototype.visitClassOptions = function(ctx) {
  return this.visitChildren(ctx);
};


// Visit a parse tree produced by DynabuffersParser#fieldOptions.
DynabuffersVisitor.prototype.visitFieldOptions = function(ctx) {
  return this.visitChildren(ctx);
};


// Visit a parse tree produced by DynabuffersParser#annotation.
DynabuffersVisitor.prototype.visitAnnotation = function(ctx) {
  return this.visitChildren(ctx);
};


// Visit a parse tree produced by DynabuffersParser#value.
DynabuffersVisitor.prototype.visitValue = function(ctx) {
  return this.visitChildren(ctx);
};



exports.DynabuffersVisitor = DynabuffersVisitor;