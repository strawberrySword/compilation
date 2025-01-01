def main():
    fnames = ['dec_list', 'var_dec', 'func_dec', 'class_dec', 'type', 'arg_list', 'class_field_list', \
              'array_def', 'binop_exp', 'func_call', 'int', 'string', 'nil', 'exp_list', 'var', \
                'stmt_assign', 'stmt_return', 'stmt_if', 'stmt_while', 'stmt_function_call', \
                    'stmt_list', 'new_exp']
    
    for name in fnames:
        cname = 'AST_'+name.upper()
        constructorCode = f'public {cname}(/*TODO*/) {{\n\t/*TODO: implement constructor(s?)*/\n\t}}'
        header = 'package AST;\nimport TYPES.*;\n\n'
        code = header + 'public class '+cname+' extends AST_Node /*TODO: determine inheritance*/' + f' {{\n\n\t{constructorCode}\n}}'
        

        with open(cname+'.java', 'w') as f:
            f.write(code)

if __name__ == '__main__':
    main()