import re, os, subprocess

def strip_java_comments(code):
    result = []
    i = 0; n = len(code)
    in_string = False; in_char = False
    while i < n:
        if not in_string and not in_char and code[i] == '"':
            in_string = True; result.append(code[i]); i += 1; continue
        if in_string:
            if code[i] == '\\': result.append(code[i]); result.append(code[i+1]); i += 2; continue
            if code[i] == '"': in_string = False
            result.append(code[i]); i += 1; continue
        if not in_char and code[i] == "'":
            in_char = True; result.append(code[i]); i += 1; continue
        if in_char:
            if code[i] == '\\': result.append(code[i]); result.append(code[i+1]); i += 2; continue
            if code[i] == "'": in_char = False
            result.append(code[i]); i += 1; continue
        if i+1 < n and code[i] == '/' and code[i+1] == '*':
            end = code.find('*/', i+2)
            i = n if end == -1 else end+2; continue
        if i+1 < n and code[i] == '/' and code[i+1] == '/':
            end = code.find('\n', i)
            i = n if end == -1 else end; continue
        result.append(code[i]); i += 1
    joined = ''.join(result)
    joined = re.sub(r'\n{3,}', '\n\n', joined)
    return '\n'.join(l.rstrip() for l in joined.split('\n')).lstrip('\n')

modificados = []
for root, dirs, files in os.walk(os.path.join("src","main","java")):
    for f in files:
        if not f.endswith('.java'): continue
        ruta = os.path.join(root, f)
        original = open(ruta, encoding='utf-8').read()
        limpio = strip_java_comments(original)
        if limpio != original:
            open(ruta, 'w', encoding='utf-8').write(limpio)
            modificados.append(ruta)
            print(f"LIMPIADO: {f} ({len(original.split(chr(10)))} -> {len(limpio.split(chr(10)))} lineas)")
        else:
            print(f"sin cambios: {f}")

if modificados:
    subprocess.run(["git","add"] + modificados, check=True)
    subprocess.run(["git","commit","-m",f"chore: strip comentarios de {len(modificados)} archivos .java"], check=True)
    print(f"\n✅ {len(modificados)} archivos limpiados y commiteados.")
else:
    print("\n✅ Todos ya estaban limpios.")