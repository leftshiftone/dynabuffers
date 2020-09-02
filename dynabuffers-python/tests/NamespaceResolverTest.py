import unittest

from dynabuffers.NamespaceResolver import NamespaceResolver
from dynabuffers.ast.ClassType import ClassType, ClassTypeOptions
from dynabuffers.ast.NamespaceType import NamespaceType, NamespaceTypeOptions


class TestNamespaceResolver(unittest.TestCase):
    def test_resolves_namespace_from_dict_1(self):
        d = {'a': 'b', ':namespace': 'first'}
        class_under_test = NamespaceResolver([
            NamespaceType(NamespaceTypeOptions("first", []), [])
        ])

        r = class_under_test.get_namespace(d)
        self.assertEqual(r.namespace.options.name, "first")
        self.assertEqual(len(r.path), 1)
        self.assertEqual(r.path[0].name, "first")
        self.assertEqual(r.path[0].position, 0)

    def test_resolves_namespace_from_dict_2(self):
        d = {'a': 'b', ':namespace': 'first.second'}
        class_under_test = NamespaceResolver([
            NamespaceType(NamespaceTypeOptions("first", []), [NamespaceType(NamespaceTypeOptions("second", []), [])])
        ])

        r = class_under_test.get_namespace(d)
        self.assertEqual(r.namespace.options.name, "second")
        self.assertEqual(len(r.path), 2)
        self.assertEqual(r.path[0].name, "first")
        self.assertEqual(r.path[0].position, 0)
        self.assertEqual(r.path[1].name, "second")
        self.assertEqual(r.path[1].position, 0)

    def test_resolves_namespace_from_dict_3(self):
        d = {'a': 'b', ':namespace': 'first.third'}
        class_under_test = NamespaceResolver([
            NamespaceType(NamespaceTypeOptions("first", []), [
                NamespaceType(NamespaceTypeOptions("second", []), []),
                NamespaceType(NamespaceTypeOptions("third", []), [])
            ])
        ])

        r = class_under_test.get_namespace(d)
        self.assertEqual(r.namespace.options.name, "third")
        self.assertEqual(len(r.path), 2)
        self.assertEqual(r.path[0].name, "first")
        self.assertEqual(r.path[0].position, 0)
        self.assertEqual(r.path[1].name, "third")
        self.assertEqual(r.path[1].position, 1)

    def test_returns_inferred_namespace(self):
        class_under_test = NamespaceResolver([
            NamespaceType(NamespaceTypeOptions("first", []), [
                NamespaceType(NamespaceTypeOptions("second", []), [
                    NamespaceType(NamespaceTypeOptions("third", []), [])
                ])
            ])
        ])
        r = class_under_test.get_namespace_from_names(None)
        self.assertEqual(r.namespace.options.name, "third")
        self.assertEqual(len(r.path), 3)
        self.assertEqual(r.path[0].name, "first")
        self.assertEqual(r.path[0].position, 0)
        self.assertEqual(r.path[1].name, "second")
        self.assertEqual(r.path[1].position, 0)
        self.assertEqual(r.path[2].name, "third")
        self.assertEqual(r.path[2].position, 0)

    def test_returns_None_if_no_namespace_is_present(self):
        class_under_test = NamespaceResolver([ClassType(ClassTypeOptions("sadasdsad", [], []))])
        r = class_under_test.get_namespace_from_names(None)
        self.assertIsNone(r)
